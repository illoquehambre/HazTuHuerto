import React from "react";
import { useEffect, useState } from "react";
import {useLocation} from 'wouter';
import NavBar from './NavBar.js'

export default function UserProfile(){
    const [location,setLocation]=useLocation();
    const [isLoading, setIsLoading] = useState(true);
    const [user, setUser] = useState([])
    const name=location.split('/').reverse()[0]
    console.log(name)
    const apiUrl= `http://localhost:8080/user/${name}`
    
    useEffect(function(){
        fetch(apiUrl,{
            method:'GET',
            headers:{
                "Content-Type":"application/json",
                "Accept":"application/json",
                "Authorization":`Bearer ${localStorage.getItem('token')}` 
            }
        })
        .then(res => {

            if (res.ok) {
            return res.json();
          }
          throw new Error('Something went wrong');
        })
        .then((data) =>{
            setUser(data)
            console.log(data)
        })
        .catch((error) => {
            console.log(error)
            setLocation('/Page404')
          });   
       setIsLoading(false)    

    }, [])
    
      
    if (isLoading) {
        return (
            <div className="App">
            <h1>Cargando...</h1>
            </div>
        );
    }
    return(
        <div>
            <NavBar></NavBar>
            <p>User Name: {user.username}</p>
            <p>Full Name: {user.fullName}</p>
            <p>Reputation: {user.reputation}</p>         
             
        </div>
        
    )
}