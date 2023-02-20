import React from "react";
import { useEffect, useState } from "react";
import {useLocation} from 'wouter';
import NavBar from './NavBar.js'


export default function QuestionList(){
    const [location,setLocation]=useLocation();
    const [isLoading, setIsLoading] = useState(true);
    const [page, setPage] = useState([])
    const name=location.split('/').reverse()[0]
    console.log(name)
    const apiUrl= `http://localhost:8080/question`
    
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
            setPage(data.content)
            console.log(data.content)
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
            {
            <ul className="column">
            {
                page.map((question) => (
                console.log(question),
                <li key={question.id}>
                    <ul className="column">
                        <li>Title: {question.title}</li>
                        <li>Content: {question.content}</li>
                        <li>Publisher: {question.publisher.username}</li>
                    </ul>
                </li>
                ))         
            }          
            </ul>
            }
        </div>
    )
        
    
}