import React from "react";
import '../styles/NavBar.css'
import { Link } from 'wouter';
import Logout from "./Logout";
import Picture from "./Picture"
import { useLocation } from 'wouter';
export default function NavBar() {
    const [location, setLocation] = useLocation();
    return (
        <div className="nav">
            <ul>
                <li><Link to={`/`}>Home</Link></li>
                <li><Link to={`/user`}>Users</Link></li>                
                <li><Link to={`/bannedUser`}>Banned Users</Link></li>
                <li><Link to={`/question`}>Questions</Link></li>
                <li><Link to={`/register`}>Register</Link></li>
                <li>
                    <div className="navBarProfile">
                        <Picture keyword={localStorage.getItem('avatar')}></Picture>
                        <Link to={`/user/${localStorage.getItem('username')}`}>Profile</Link>
                        
                    </div>
                </li>
            </ul>
        </div>

    )
}