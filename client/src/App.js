import './App.css';
import React from 'react';
import Draft from './Draft';
import ViewStandings from './ViewStandings';
import ViewSchedule from './ViewSchedule';
import PlaySeason from './PlaySeason';
import TeamsCreation from './TeamsCreation';
import SeasonFacts from './SeasonFacts';

import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";


function App() {
  return (
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
        <Route path="/draft">
          <Draft />
        </Route>
        <Route path="/">
          <TeamsCreation />
        </Route>
      </Switch>
    </Router>
  );
}

export default App;
