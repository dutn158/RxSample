package trandu.com.rxsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;
import trandu.com.rxsample.dto.Athlete;
import trandu.com.rxsample.dto.Team;
import trandu.com.rxsample.dto.User;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Observable<List<String>> observable3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Subscriber<List<Athlete>> subscriber1 = new Subscriber<List<Athlete>>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted() called");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError() called with: e = [" + e + "]");
            }

            @Override
            public void onNext(List<Athlete> athletes) {
                Log.e(TAG, "Number of athlete : " + athletes.size() + "");
            }
        };

        Subscriber<List<String>> subscriber2 = new Subscriber<List<String>>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted() called");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError() called with: e = [" + e + "]");
            }

            @Override
            public void onNext(List<String> strings) {
                for (int i = 0; i < strings.size(); i++) {
                    Log.e(TAG, strings.get(i));
                }
            }
        };

        Subscriber<List<String>> subscriber3 = new Subscriber<List<String>>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted() called");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError() called with: e = [" + e + "]");
            }

            @Override
            public void onNext(List<String> strings) {
                for (int i = 0; i < strings.size(); i++) {
                    Log.e(TAG, strings.get(i));
                }
            }
        };

        Observable<List<Team>> observable = Observable.create(new Observable.OnSubscribe<List<Team>>() {
            @Override
            public void call(Subscriber<? super List<Team>> subscriber) {
                List<Team> teams = getTeams();
                if (teams == null) {
                    subscriber.onError(new Exception());
                }
                subscriber.onNext(teams);
                subscriber.onCompleted();
            }
        });

        observable.map(new Func1<List<Team>, List<Athlete>>() {
            @Override
            public List<Athlete> call(List<Team> teams) {
                List<Athlete> athletes = new ArrayList<Athlete>();
                for (Team team : teams) {
                    List<Athlete> a = team.athletes;
                    for (Athlete athlete : a) {
                        athletes.add(athlete);
                    }
                }
                return athletes;
            }
        }).subscribe(subscriber1);

        observable3 = observable.map(new Func1<List<Team>, List<String>>() {
            @Override
            public List<String> call(List<Team> teams) {
                List<String> names = new ArrayList<String>();
                for (Team team : teams) {
                    names.add(team.name);
                }
                return names;
            }
        });

        observable3.subscribe(subscriber2);
        observable3.subscribe(subscriber3);


        Observable<User> u1 = Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                User user1 = getUser("User Android");
                subscriber.onNext(user1);
                subscriber.onCompleted();
            }
        });

        Observable<User> u2 = Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                User user2 = getUser("User iOS");
                subscriber.onNext(user2);
                subscriber.onCompleted();
            }
        });

        Subscriber<String> subscriber4 = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted() called");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError() called with: e = [" + e + "]");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext() called with: s = [" + s + "]");
            }
        };

        Observable.zip(u1, u2, new Func2<User, User, String>() {
            @Override
            public String call(User user, User user2) {
                return user.userName + " " + user2.userName;
            }
        }).subscribe(subscriber4);


    }

    public List<Team> getTeams() {
        List<Team> teams = new ArrayList<>();
        Team team = new Team();
        Athlete a1 = new Athlete("athlete1", 20);
        Athlete a2 = new Athlete("athlete2", 20);
        Athlete a3 = new Athlete("athlete3", 20);
        Athlete a4 = new Athlete("athlete4", 20);
        Athlete a5 = new Athlete("athlete5", 20);
        Athlete a6 = new Athlete("athlete6", 20);
        Athlete a7 = new Athlete("athlete7", 20);
        Athlete a8 = new Athlete("athlete8", 20);

        team.name = "Team 1";
        team.athletes = new ArrayList<>();
        team.athletes.add(a1);
        team.athletes.add(a2);
        team.athletes.add(a3);
        team.athletes.add(a4);
        team.athletes.add(a5);
        team.athletes.add(a6);
        team.athletes.add(a7);
        team.athletes.add(a8);

        //
        Team team2 = new Team();
        Athlete a11 = new Athlete("athlete1", 20);
        Athlete a12 = new Athlete("athlete2", 20);
        Athlete a13 = new Athlete("athlete3", 20);
        Athlete a14 = new Athlete("athlete4", 20);
        Athlete a15 = new Athlete("athlete5", 20);
        Athlete a16 = new Athlete("athlete6", 20);
        Athlete a17 = new Athlete("athlete7", 20);
        Athlete a18 = new Athlete("athlete8", 20);

        team2.name = "Team 2";
        team2.athletes = new ArrayList<>();
        team2.athletes.add(a11);
        team2.athletes.add(a12);
        team2.athletes.add(a13);
        team2.athletes.add(a14);
        team2.athletes.add(a15);
        team2.athletes.add(a16);
        team2.athletes.add(a17);
        team2.athletes.add(a18);

        teams.add(team);
        teams.add(team2);

        return teams;
    }

    public User getUser(String name) {
        User user = new User(name);
        return user;
    }
}
