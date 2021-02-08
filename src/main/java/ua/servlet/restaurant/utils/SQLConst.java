package ua.servlet.restaurant.utils;

public interface SQLConst {
//     String PROP_FILE = "application.properties";
     String PROP_FILE = "app.properties";
     String CONN_PROP = "connection.url";

     String ID = "id";
     String USER_LOGIN = "login";
     String TEAM_NAME = "name";

     // DBLogins
     String SQL_INSERT_USER = "INSERT INTO login (login) VALUES (?)";
     String DBE_INSERT_USER = "Cannot insert user ";

     String SQL_GET_USER = "SELECT id FROM login WHERE login=?";
     String DBE_GET_USER = "Cannot get user ";

     String SQL_GET_All_USERS = "SELECT * FROM login";


     String SQL_INSERT_TEAM = "INSERT INTO teams (name) VALUES (?)";

     String SQL_GET_TEAM = "SELECT id FROM teams WHERE name=?";
     String SQL_GET_TEAMS_BY_USER = "SELECT teams.id, teams.name FROM users_teams RIGHT JOIN teams " +
            "ON users_teams.team_id=teams.id WHERE user_id=?";
     String SQL_SET_TEAMS_FOR_USER = "INSERT INTO users_teams (user_id, team_id) VALUES (?,?)";
     String SQL_FIND_ALL_USERS = "SELECT * FROM users";
     String SQL_FIND_ALL_TEAMS = "SELECT * FROM teams";
     String SQL_UPDATE_TEAM = "UPDATE teams SET name=? WHERE id=?";
     String SQL_DELETE_TEAM = "DELETE FROM teams WHERE id=?";


     String DBE_INSERT_TEAM = "Cannot insert team ";

     String DBE_GET_TEAM = "Cannot get team ";
     String DBE_GET_TEAMS_BY_USER = "Cannot get teams where user is ";
     String DBE_SET_TEAMS_FOR_USER = "Cannot set teams for user ";
     String DBE_FIND_ALL_USERS = "Cannot find all users\n";
     String DBE_FIND_ALL_TEAMS = "Cannot find all teams\n";
     String DBE_UPDATE_TEAM = "Cannot update team with id=";
     String DBE_DELETE_TEAM = "Cannot delete team with id=";
}
