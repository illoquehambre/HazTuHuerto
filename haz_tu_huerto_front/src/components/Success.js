import React from "react";

export default function Success(){
    return(
        <div>
            <p>oleeee</p>
            <p>Token: {localStorage.getItem('username', 'user'['username'])}</p>
        </div>
        
    )
}