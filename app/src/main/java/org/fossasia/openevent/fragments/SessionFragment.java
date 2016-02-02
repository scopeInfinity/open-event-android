package org.fossasia.openevent.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.fossasia.openevent.OpenEventApp;
import org.fossasia.openevent.R;
import org.fossasia.openevent.activities.ScheduleSessionDetailActivity;
import org.fossasia.openevent.adapters.ScheduleSessionsListAdapter;
import org.fossasia.openevent.api.Urls;
import org.fossasia.openevent.data.Session;
import org.fossasia.openevent.dbutils.DbSingleton;
import org.fossasia.openevent.utils.ISO8601Date;
import org.fossasia.openevent.utils.IntentStrings;
import org.fossasia.openevent.utils.RecyclerItemClickListener;
import org.fossasia.openevent.utils.SimpleDividerItemDecoration;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by duncanleo on 1/2/16.
 */
public class SessionFragment extends Fragment {
    //TODO: Set to the actual first day of the event
    public static final long FIRST_DAY_MILLIS = new GregorianCalendar(2015, 4, 5).getTime().getTime();

    private RecyclerView sessionsRecyclerView;
    private ScheduleSessionsListAdapter sessionsListAdapter;
    private List<Session> data;
    private int tabPos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tabPos = getArguments().getInt(ScheduleFragment.SCHEDULE_TAB_POSITION);
        data = DbSingleton.getInstance().getSessionList();

        //TODO: Use database to filter
        Iterator<Session> sessionIterator = data.iterator();
        while (sessionIterator.hasNext()) {
            Session s = sessionIterator.next();
            Date d = ISO8601Date.getDateObject(s.getStartTime());
            long diff = d.getTime() - FIRST_DAY_MILLIS;
            if (TimeUnit.MILLISECONDS.toDays(diff) != tabPos) {
                sessionIterator.remove();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_session, container, false);
        sessionsRecyclerView = (RecyclerView)v.findViewById(R.id.sessionRecyclerView);
        sessionsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        sessionsListAdapter = new ScheduleSessionsListAdapter(data, tabPos);
        sessionsRecyclerView.setAdapter(sessionsListAdapter);
        sessionsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        sessionsRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity().getResources()));
        sessionsRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ScheduleSessionDetailActivity.class);
                intent.putExtra(IntentStrings.SESSION, data.get(position).getTitle());
                startActivity(intent);
            }
        }));
        return v;
    }

    public ScheduleSessionsListAdapter getSessionsListAdapter() {
        return sessionsListAdapter;
    }
}
