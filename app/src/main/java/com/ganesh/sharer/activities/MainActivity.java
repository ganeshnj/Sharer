package com.ganesh.sharer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ganesh.sharer.Repository;
import com.ganesh.sharer.fragments.EventsFragment;
import com.ganesh.sharer.fragments.FriendsFragment;
import com.ganesh.sharer.fragments.GroupsFragment;
import com.ganesh.sharer.R;
import com.ganesh.sharer.fragments.SettlementFragment;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //private DatabaseContext mDatabaseContext = new DatabaseContext();

    private FloatingActionMenu mFamAdd;
    private FloatingActionButton mFabAddFriend;
    private FloatingActionButton mFabAddGroup;
            private FloatingActionButton mFabAddEvent;
            private FloatingActionButton mFadAddSettlement;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFamAdd = (FloatingActionMenu) findViewById(R.id.famAdd);

        mFabAddFriend = (FloatingActionButton) findViewById(R.id.fabAddFriend);
        mFabAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddFriendActivity.class);
                MainActivity.this.startActivity(intent);
                mFamAdd.toggle(true);
            }
        });

        mFabAddGroup = (FloatingActionButton) findViewById(R.id.fabAddGroup);
        mFabAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddGroupActivity.class);
                MainActivity.this.startActivity(intent);
                mFamAdd.toggle(true);
            }
        });

        mFabAddEvent = (FloatingActionButton) findViewById(R.id.fabAddEvent);
        mFabAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEventActivity.class);
                MainActivity.this.startActivity(intent);
                mFamAdd.toggle(true);
            }
        });

        mFadAddSettlement = (FloatingActionButton) findViewById(R.id.fabAddSettlement);
        mFadAddSettlement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddSettlementActivity.class);
                MainActivity.this.startActivity(intent);
                mFamAdd.toggle(true);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        updateLoginInfo();

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, EventsFragment.newInstance(Repository.getAllEvents())).commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_activity) {
            // Handle the camera action
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, EventsFragment.newInstance(Repository.getAllEvents())).commitAllowingStateLoss();
        } else if (id == R.id.nav_friends) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, FriendsFragment.newInstance(Repository.getAllUsers())).commitAllowingStateLoss();
        } else if (id == R.id.nav_groups) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, GroupsFragment.newInstance(Repository.getAllGroups())).commitAllowingStateLoss();
        }
        else if (id == R.id.nav_settlements) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, SettlementFragment.newInstance(Repository.getAllSettlements())).commitAllowingStateLoss();
        }
        else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            this.startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateLoginInfo() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        TextView mTextViewLoginedEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textViewLoginedUserEmail);
        mTextViewLoginedEmail.setText(Repository.getLoginedUser().getEmail());

        TextView mTextViewLoginedName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textViewLoginedUserName);
        mTextViewLoginedName.setText(Repository.getLoginedUser().getName());
    }
}
