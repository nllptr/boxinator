import React from 'react';
import { HashRouter, Route, Switch } from 'react-router-dom';

import CreateShipmentPage from './components/CreateShipmentPage';
import ShipmentsPage from './components/ShipmentsPage';

import './App.css';

function App() {
  return (
    <div className="App">
      <HashRouter>
        <Switch>
          <Route path="/addbox" component={CreateShipmentPage} />
          <Route path="/listboxes" component={ShipmentsPage} />
        </Switch>
      </HashRouter>
    </div>
  );
}

export default App;
