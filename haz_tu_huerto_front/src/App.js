import {Route, Switch} from 'wouter';
import { useEffect, useState } from "react";
import Home from './components/Home'
import Login from './components/Login'
import Success from './components/Success'
import UserProfile from './components/UserProfile'
import Page404 from './components/Page404'
import UserList from './components/UserList'
import QuestionList from './components/QuestionList'
import Signup from './components/Signup'
import './styles/App.css';

function App() {
  return (
    <div className="App">
      <header className="App-header">
      <Switch>
              <Route              
                path="/"
                component={Home}
              />
              <Route              
                path="/login"
                component={Login}
              />
              <Route              
                path="/success"
                component={Success}
              />
              <Route              
                path="/user/:name"
                component={UserProfile}
              />
               <Route              
               path="/user"
                component={UserList}
              />
              <Route              
               path="/question"
                component={QuestionList}
              />
              <Route              
               path="/signup"//Para la pagina de error
                component={Signup}
              />
              <Route              
               path="/:rest*"//Para la pagina de error
                component={Page404}
              />
             
            </Switch>
      </header>
    </div>
  );
}

export default App;
