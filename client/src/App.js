import React, { useState } from 'react';
import jwt_decode from 'jwt-decode';

import './App.css';
//import React from 'react';
import Draft from './Draft';
import ViewStandings from './ViewStandings';
import ViewSchedule from './ViewSchedule';
import PlaySeason from './PlaySeason';
import TeamsCreation from './TeamsCreation';
import SeasonFacts from './SeasonFacts';
import ViewFreeAgents from './ViewFreeAgents';
import Login from './Login'
import AuthContext from './AuthContext';

import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  Redirect
} from "react-router-dom";

function Users() {
  return <h1>Users</h1>;
}

function NotFound() {
  return <h1>Not Found</h1>;
}

function App() {
  const [user, setUser] = useState(null);

  const login = (token) => {

    const { appUserId, sub: username, authorities } = jwt_decode(token);

    // Split the authorities into an array of roles.
    const roles = authorities.split(',');
  
    const user = {
      appUserId: parseInt(appUserId, 10),
      username,
      roles,
      token,
      hasRole(role) {
        return this.roles.includes(role);
      }
    };
  
    console.log(user);

    setUser(user);

    return user;
  };

  const logout = () => {
    setUser(null);
  };

  // Create the auth object
  const auth = {
    user,
    login,
    logout
  };

  return (
    <AuthContext.Provider value={auth}>
      <Router>
        <Switch>
          <Route path="/SeasonFacts">
            <SeasonFacts />
          </Route>
          <Route path="/PlaySeason">
            <PlaySeason />
          </Route>
          <Route path="/ViewSchedule">
            <ViewSchedule />
          </Route>
          <Route path="/ViewStandings">
            <ViewStandings />
          </Route>
          <Route path="/ViewFreeAgents">
            <ViewFreeAgents />
          </Route>
          <Route path="/draft">
            <Draft />
          </Route>
          <Route path="/login">
            <Login />
          </Route>
          <Route path="/">
            <TeamsCreation />
          </Route>
        </Switch>
      </Router>
    </AuthContext.Provider>
  );
}

export default App;
