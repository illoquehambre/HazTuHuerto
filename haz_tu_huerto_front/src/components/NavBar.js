import React from "react";
import '../styles/NavBar.css'
import {Link} from 'wouter';
import Logout from "./Logout";
export default function NavBar() {
    return (
        <div className="nav">
            <ul>
                <li><Link to={`/`}>Home</Link></li>
                <li><Link to={`/user`}>Users</Link></li>
                <li><Link to={`/question`}>Questions</Link></li>
                <li><Logout></Logout></li>
            </ul>
        </div>

    )
}