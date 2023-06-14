import React from "react";
import { useLocation } from "wouter";
import Swal from "sweetalert2";
import NavBar from './NavBar.js'
import { Link } from "wouter";
import swal from "sweetalert";
import '../styles/Login.css'
//import {useHistory} from 'react-router-dom'
import { useEffect, useState } from "react";
async function signUser(id) {
    console.log('hola '+id)
  return fetch(`http://localhost:8080/admin/user/${id}`, {
    method: "Delete",
    headers: {
      "Content-Type": "application/json",
      "Accept": "application/json",
      "Authorization": `Bearer ${localStorage.getItem('token')}`
    },
  
  });

  //localStorage.setItem(JSON.stringify(result))
}
export default function Bann (prop){
    console.log(prop)
    console.log(prop.user)
    console.log(prop.user.id)
    
  const [id, setId] = useState(prop.user.id);

  console.log(id)


  const handleSubmit = async (e) => {
    e.preventDefault();
    const response = await signUser(
      id
    );
      console.log(response);
      Swal.fire({
        icon: 'success',
        title: 'oleeee',
        text: `User ${prop.username} banned successfully`,
        showConfirmButton: false,
        timer: 2000,

      })
    
  };

  return (
    <button onClick={handleSubmit}>Bann user</button>
  );
}
