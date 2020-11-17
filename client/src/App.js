import './App.css';
import React from 'react';
import Draft from './Draft';
import TeamsCreation from './TeamsCreation';
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
