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
import QuestionDetails from './components/QuestionDetails'
import './styles/App.css';
import { Helmet,HelmetProvider  } from "react-helmet-async";
import NavBar from './components/NavBar';
import UserBannedList from './components/UserBannedList';

function App() {
  return (
    <HelmetProvider>
    <div className="App">
      <header className="App-header">
      <Switch>
              <Route              
                path="/"
                component={Home}
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
               path="/question/:id"//No funciona
                component={QuestionDetails}
              />
              <Route              
               path="/register"//Para la pagina de error
                component={Signup}
              />
              <Route              
               path="/nav"//Para la pagina de error
                component={NavBar}
              />
              <Route              
               path="/bannedUser"//Para la pagina de error
                component={UserBannedList}
              />
              <Route              
               path="/:rest*"//Para la pagina de error
                component={Page404}
              />
              
             
            </Switch>
      </header>
    </div>
    </HelmetProvider>
  );
}

export default App;
