import React from "react";
import { useLocation } from "wouter";
import Swal from "sweetalert2";
import { Link } from "wouter";
import swal from "sweetalert";
import '../styles/Login.css'
//import {useHistory} from 'react-router-dom'
import { useEffect, useState } from "react";
async function loginUser(credentials) {
  return fetch("http://localhost:8080/auth/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Accept: "application/json",
    },
    body: JSON.stringify(credentials),
  }).then((data) => data.json());

  //localStorage.setItem(JSON.stringify(result))
}
export default function Login() {
  const [, setLocation] = useLocation();
  const [username, setUsername] = useState();
  const [password, setPassword] = useState();

  const handleSubmit = async (e) => {
    console.log(e)
    e.preventDefault();
    const response = await loginUser({
      username,
      password,
    });
    if ("token" in response) {
      console.log(response);
      Swal.fire({
        icon: 'success',
        title: 'oleeee',
        text: 'Welcome',
        showConfirmButton: false,
        timer: 2000,
  
      }).then(() => {
        
        localStorage.setItem("token", response["token"]);
        localStorage.setItem("username", response["username"]);
        localStorage.setItem("avatar", response["avatar"]);
        console.log(localStorage.getItem("token"));
        console.log(localStorage.getItem("username"));        
        const name = response["username"];
        //localStorage.setItem('user', JSON.stringify(response['user']));
        setLocation(`/user/${name}`);
      });
    } else {
      console.log(response);
      console.log(response.status);
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: response.message,
      })
    }
  };

  return (
    /*
        <form  noValidate onSubmit={handleSubmit}>
            <input type="text" id="username" required name="username" placeholder="username" onChange={(e)=>setUsername(e.target.value)}></input>
            <input  type="password" id="password" required name="password" autoComplete="on"  placeholder="password"onChange={(e)=>setPassword(e.target.value)}></input>
            <button type="submit">Submit</button>
        </form>
       */
  
      <form onSubmit={handleSubmit}>
        <div className="user-box">
          <input type="text" id="logUsername" required name="username"  onChange={(e)=>setUsername(e.target.value)}></input>
          <label>Username</label>
        </div>
        <div className="user-box">
          <input type="password" id="logPassword" required name="password" autoComplete="on" onChange={(e)=>setPassword(e.target.value)} ></input>
          <label>Password</label>
        </div>
        <button type="submit">
          <span></span>
          <span></span>
          <span></span>
          <span></span>
          Submit
        </button>
      </form>
  
  );
}
