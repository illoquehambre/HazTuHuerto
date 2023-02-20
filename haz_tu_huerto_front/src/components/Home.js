import React from "react";
import {Link} from 'wouter';
export default function PokemonDetails(){
    return(
        <div>
            <button ><Link to={`/login`}>Login</Link></button>
            <br></br>
            <button ><Link to={`/signup`}>Signup</Link></button>
        </div>
            
      
        
    )
}