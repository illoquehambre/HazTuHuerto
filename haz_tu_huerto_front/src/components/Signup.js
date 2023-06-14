import React from "react";
import { useLocation } from "wouter";
import Swal from "sweetalert2";
import NavBar from './NavBar.js'
import { Link } from "wouter";
import swal from "sweetalert";
import '../styles/Login.css'
//import {useHistory} from 'react-router-dom'
import { useEffect, useState } from "react";
async function signUser(credentials) {
  return fetch("http://localhost:8080/auth/register/admin", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Accept": "application/json",
      "Authorization": `Bearer ${localStorage.getItem('token')}`
    },
    body: JSON.stringify(credentials),
  }).then((data) => data.json());

  //localStorage.setItem(JSON.stringify(result))
}
export default function Signup() {
  const [location, setLocation] = useLocation(); const [username, setUsername] = useState();
  const [password, setPassword] = useState();
  const [verifyPassword, setVerifyPassword] = useState();
  const [fullName, setFullName] = useState();
  const [email, setEmail] = useState();
  const [verifyEmail, setVerifyEmail] = useState();

  const handleSubmit = async (e) => {
    e.preventDefault();
    const response = await signUser({
      username,
      password,
      verifyPassword,
      fullName,
      email,
      verifyEmail
    });
    if ("id" in response) {
      console.log(response);
      Swal.fire({
        icon: 'success',
        title: 'oleeee',
        text: `User ${response.username} created successfully socio`,
        showConfirmButton: false,
        timer: 2000,

      })
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
    <div>
      <NavBar></NavBar>
      {
        <div className="package">
        
        <div className="login-box">
          <h3>Register New Admin</h3>
          <form noValidate onSubmit={handleSubmit}>
            <div className="user-box">
              <input type="text" id="signUsername" required name="username" onChange={(e) => setUsername(e.target.value)}></input>
              <label>Username</label>
            </div>
            <div className="user-box">
              <input type="password" id="signPassword" required name="password" autoComplete="on" onChange={(e) => setPassword(e.target.value)} ></input>
              <label>Password</label>
            </div>
            <div className="user-box">
              <input type="password" id="verifyPassword" required name="verifyPassword" autoComplete="on" onChange={(e) => setVerifyPassword(e.target.value)} ></input>
              <label>Verify Password</label>
            </div>
            <div className="user-box">
              <input type="text" id="fullName" required name="fullName" autoComplete="on" onChange={(e) => setFullName(e.target.value)} ></input>
              <label>Full Name</label>
            </div>
            <div className="user-box">
              <input type="email" id="email" required name="email" autoComplete="on" onChange={(e) => setEmail(e.target.value)} ></input>
              <label>Email</label>
            </div>
            <div className="user-box">
              <input type="email" id="verifyEmail" required name="verifyEmail" autoComplete="on" onChange={(e) => setVerifyEmail(e.target.value)} ></input>
              <label>Verify Email</label>
            </div>
            <button type="submit">
              <span></span>
              <span></span>
              <span></span>
              <span></span>
              Submit
            </button>
          </form>
        </div>
        </div>
      }
    </div>
  );
}
