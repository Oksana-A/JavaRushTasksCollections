package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery {
    private Path logDir;

    public LogParser(Path logDir) {
        this.logDir = logDir;
    }

    private List<MyLog> getLogs() {
        List<MyLog> myLogList = new ArrayList<>();
        List<Path> pathList = new ArrayList<>();
        try {
            pathList = Files.list(logDir).
                    filter(x -> x.toString().endsWith(".log")).
                    collect(Collectors.toList());
            for (Path path: pathList) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path.toString())));
                String strLine;
                while ((strLine = reader.readLine()) != null) {
                    String[] strArray = strLine.split("\t");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

                    String id = strArray[0].trim();
                    String name = strArray[1];
                    Date date = dateFormat.parse(strArray[2]);
                    Event event = Enum.valueOf(Event.class, (strArray[3].split(" "))[0]);
                    int task = -1;
                    if (event == Event.DONE_TASK || event == Event.SOLVE_TASK)
                        task = Integer.parseInt(strArray[3].split(" ")[1]);
                    Status status = Enum.valueOf(Status.class, strArray[4].trim());
                    MyLog myLog = new MyLog(id, name,date, event, status);
                    if (task != -1)
                        myLog.setTask(task);
                    myLogList.add(myLog);
                }
            }
        }
        catch (IOException | ParseException e) {
            System.out.println(e.getMessage());
        }
        return myLogList;
    }

    private Stream<MyLog> filteredList(List<MyLog> myLogList, Date after, Date before) {
        if (after == null && before == null) {
            return myLogList.stream();
        } else
        if (after == null) {
            return myLogList.stream().
                    filter(x -> x.getDate().compareTo(before) <= 0);
        } else
        if (before == null) {
            return myLogList.stream().
                    filter(x -> x.getDate().compareTo(after) >= 0);
        }
        else {
            return myLogList.stream().
                    filter(x -> x.getDate().compareTo(before) <= 0).
                    filter(x -> x.getDate().compareTo(after) >= 0);
        }
    }

    //implements QLQuery
    @Override
    public Set<Object> execute(String query) {
        if (query.startsWith("get ")) {
            String[] strArray = query.split(" ");
            if (strArray.length == 2) {
                Set<Object> set = new HashSet<>();
                switch (query) {
                    case "get ip":
                        set = getLogs().stream().map(MyLog::getIp).distinct().collect(Collectors.toSet());
                        break;
                    case "get user":
                        set = getLogs().stream().map(MyLog::getUser).distinct().collect(Collectors.toSet());
                        break;
                    case "get date":
                        set = getLogs().stream().map(MyLog::getDate).distinct().collect(Collectors.toSet());
                        break;
                    case "get event":
                        set = getLogs().stream().map(MyLog::getEvent).distinct().collect(Collectors.toSet());
                        break;
                    case "get status":
                        set = getLogs().stream().map(MyLog::getStatus).distinct().collect(Collectors.toSet());
                        break;
                }
                return set;
            }
            if (strArray.length >= 6) {
                //get event for date = "03.01.2014 03:45:23"
                Set<Object> set = null;
                int indexStartField1 = query.lastIndexOf("get ") + 4;
                int indexEndField1 = query.indexOf(" for ");
                String field1 = query.substring(indexStartField1, indexEndField1);
                int indexStartField2 = query.lastIndexOf(" for ") + 5;
                int indexEndField2 = query.indexOf(" = \"");
                String filed2 = query.substring(indexStartField2, indexEndField2);
                int indexStartValue1 = query.lastIndexOf(" = \"") + 4;
                String value1 = query.substring(indexStartValue1).trim();
                value1 = value1.substring(0, value1.length()-1);
                Set<MyLog> myLogSet = new HashSet<>();
                switch (filed2) {
                    case "ip":
                        for (MyLog ml : getLogs())
                            if (ml.getIp().trim().equals(value1)) myLogSet.add(ml);
                        break;
                    case "user":
                        for (MyLog ml : getLogs())
                            if (ml.getUser().trim().equals(value1)) myLogSet.add(ml);
                        break;
                    case "date":
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy H:m:s");
                        try {
                            Date date = dateFormat.parse(value1);
                            for (MyLog ml : getLogs())
                                if (ml.getDate().equals(date)) myLogSet.add(ml);
                        } catch (ParseException e) {
                            System.out.println("Error");
                        }
                        break;
                    case "event":
                        Event event = Enum.valueOf(Event.class, value1);
                        for (MyLog ml : getLogs())
                            if (ml.getEvent() == event) myLogSet.add(ml);
                        break;
                    case "status":
                        Status status = Enum.valueOf(Status.class, value1);
                        for (MyLog ml : getLogs())
                            if (ml.getStatus() == status) myLogSet.add(ml);
                        break;
                }
                if (!myLogSet.isEmpty()) {
                    switch (field1) {
                        case "ip":
                            set = myLogSet.stream().map(MyLog::getIp).collect(Collectors.toSet());
                            break;
                        case "user":
                            set = myLogSet.stream().map(MyLog::getUser).collect(Collectors.toSet());
                            break;
                        case "date":
                            set = myLogSet.stream()
                                    .map(MyLog::getDate)
                                    .collect(Collectors.toSet());
                            break;
                        case "event":
                            set = myLogSet.stream().map(MyLog::getEvent).collect(Collectors.toSet());
                            break;
                        case "status":
                            set = myLogSet.stream().map(MyLog::getStatus).collect(Collectors.toSet());
                            break;
                    }
                }
                return set;
            }
        }
        return null;
    }



    // implements IPQuery
    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {
        return getUniqueIPs(after, before).size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        return  filteredList(getLogs(), after, before).map(MyLog::getIp).distinct().collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        return filteredList(getLogs(), after, before).filter(x -> x.getUser().equals(user))
                .map(MyLog::getIp).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        return filteredList(getLogs(), after, before).filter(x -> x.getEvent().equals(event))
                .map(MyLog::getIp).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        return filteredList(getLogs(), after, before).filter(x -> x.getStatus().equals(status))
        .map(MyLog::getIp).collect(Collectors.toSet());
    }


    //implements UserQuery
    @Override
    public Set<String> getAllUsers() {
        List<MyLog> myLogList = getLogs();
        Set<String> userSet = new HashSet<>();
        for (MyLog ml : myLogList)
            userSet.add(ml.getUser());
        return userSet;
    }

    @Override
    public int getNumberOfUsers(Date after, Date before) {
        return (int) filteredList(getLogs(), after, before).map(MyLog::getUser)
                .distinct().count();
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
        return (int) filteredList(getLogs(), after, before)
                .filter(x -> x.getUser().equals(user)).map(MyLog::getEvent).distinct().count();
    }

    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        return filteredList(getLogs(), after, before)
                .filter(x -> x.getIp().equals(ip)).map(MyLog::getUser).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
        return filteredList(getLogs(), after, before)
                .filter(x -> x.getEvent() == Event.LOGIN).map(MyLog::getUser).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        return filteredList(getLogs(), after, before)
                .filter(x -> x.getEvent() == Event.DOWNLOAD_PLUGIN)
                .filter(x -> x.getStatus() == Status.OK).map(MyLog::getUser).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        return filteredList(getLogs(), after, before)
                .filter(x -> x.getEvent() == Event.WRITE_MESSAGE)
                .filter(x -> x.getStatus() == Status.OK).map(MyLog::getUser).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        return filteredList(getLogs(), after, before)
                .filter(x -> x.getEvent() == Event.SOLVE_TASK).map(MyLog::getUser).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        return filteredList(getLogs(), after, before)
                .filter(x -> x.getEvent() == Event.SOLVE_TASK)
                .filter(x -> x.getTask() == task).map(MyLog::getUser).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        return filteredList(getLogs(), after, before)
                .filter(x -> x.getEvent() == Event.DONE_TASK).map(MyLog::getUser).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        return filteredList(getLogs(), after, before)
                .filter(x -> x.getEvent() == Event.DONE_TASK)
                .filter(x -> x.getTask() == task).map(MyLog::getUser).collect(Collectors.toSet());
    }

    //implements DateQuery
    @Override
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        return filteredList(getLogs(), after, before)
                .filter(x -> x.getUser().equals(user))
                .filter(x -> x.getEvent() == event).map(MyLog::getDate).collect(Collectors.toSet());
    }

    @Override
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
        return filteredList(getLogs(), after, before)
                .filter(x -> x.getStatus() == Status.FAILED).map(MyLog::getDate).collect(Collectors.toSet());
    }

    @Override
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
        return filteredList(getLogs(), after, before)
                .filter(x -> x.getStatus() == Status.ERROR).map(MyLog::getDate).collect(Collectors.toSet());
    }

    @Override
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {
        return filteredList(getLogs(), after, before)
                .filter(x -> x.getUser().equals(user))
                .filter(x -> x.getEvent() == Event.LOGIN)
                .filter(x -> x.getStatus() == Status.OK).map(MyLog::getDate).min(Date::compareTo).get();
    }

    @Override
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
        return filteredList(getLogs(), after, before)
                .filter(x -> x.getUser().equals(user))
                .filter(x -> x.getEvent() == Event.SOLVE_TASK)
                .filter(x -> x.getTask() == task).map(MyLog::getDate).min(Date::compareTo).get();
    }

    @Override
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
         return filteredList(getLogs(), after, before)
                .filter(x -> x.getUser().equals(user))
                .filter(x -> x.getEvent() == Event.DONE_TASK)
                .filter(x -> x.getTask() == task).map(MyLog::getDate).min(Date::compareTo).get();
    }

    @Override
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        return filteredList(getLogs(), after, before)
                .filter(x -> x.getUser().equals(user))
                .filter(x -> x.getEvent() == Event.WRITE_MESSAGE)
                .filter(x -> x.getStatus() == Status.OK).map(MyLog::getDate).collect(Collectors.toSet());
    }

    @Override
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        return filteredList(getLogs(), after, before)
                .filter(x -> x.getUser().equals(user))
                .filter(x -> x.getEvent() == Event.DOWNLOAD_PLUGIN)
                .filter(x -> x.getStatus() == Status.OK).map(MyLog::getDate).collect(Collectors.toSet());
    }

    //implements EventQuery
    @Override
    public int getNumberOfAllEvents(Date after, Date before) {
        return (int) filteredList(getLogs(), after, before)
                .map(MyLog::getEvent).distinct().count();
    }

    @Override
    public Set<Event> getAllEvents(Date after, Date before) {
        return filteredList(getLogs(), after, before)
                .map(MyLog::getEvent).distinct().collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        return filteredList(getLogs(), after, before).filter(x -> x.getIp().
                equals(ip)).map(MyLog::getEvent).distinct().collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        return filteredList(getLogs(), after, before).filter(x -> x.getUser().
                equals(user)).map(MyLog::getEvent).distinct().collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getFailedEvents(Date after, Date before) {
        return filteredList(getLogs(), after, before).filter(x -> x.getStatus() == Status.FAILED)
                .map(MyLog::getEvent).distinct().collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getErrorEvents(Date after, Date before) {
        return filteredList(getLogs(), after, before).filter(x -> x.getStatus() == Status.ERROR)
                .map(MyLog::getEvent).distinct().collect(Collectors.toSet());
    }

    @Override
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        return (int) filteredList(getLogs(), after, before).filter(x -> x.getEvent() == Event.SOLVE_TASK)
                .filter(x -> x.getTask() == task).count();
    }

    @Override
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        return (int) filteredList(getLogs(), after, before).filter(x -> x.getEvent() == Event.DONE_TASK)
                .filter(x -> x.getTask() == task).count();
    }

    @Override
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
        return filteredList(getLogs(), after, before).filter(x -> x.getEvent() == Event.SOLVE_TASK)
                .map(MyLog::getTask).collect(Collectors.groupingBy(
                Function.identity(), Collectors.reducing(0, e -> 1, Integer::sum)));
    }

    @Override
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
        return filteredList(getLogs(), after, before).filter(x -> x.getEvent() == Event.DONE_TASK)
                .map(MyLog::getTask).collect(Collectors.groupingBy(
                        Function.identity(), Collectors.reducing(0, e -> 1, Integer::sum)));
    }
}