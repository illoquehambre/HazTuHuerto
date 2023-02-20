import React from "react";
import { useLocation } from "wouter";
import Swal from "sweetalert2";
import { Link } from "wouter";
//import {useHistory} from 'react-router-dom'
import { useEffect, useState } from "react";

export default function Logout() {
    const [, setLocation] = useLocation();
    const logOut = async (e) => {
        e.preventDefault();
        localStorage.removeItem('token')
        setLocation('/')
    };
    return (
        <button onClick={logOut}>Logout</button>
    )
}