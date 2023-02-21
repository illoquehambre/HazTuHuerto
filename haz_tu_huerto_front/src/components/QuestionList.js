import React from "react";
import { useEffect, useState } from "react";
import {useLocation} from 'wouter';
import NavBar from './NavBar.js'
import {Link} from 'wouter';
import "../styles/List.css";



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
        <div class="courses-container">
          {page.map(
            (question) => (
              console.log(question),
              (
                <div class="course" key={question.id}>
                  <div class="course-info">
                    <h2>{question.title}</h2>
                    <h6>{question.content}</h6>
                                        
                    <p>Published at: {question.createdAt}</p>
                    <p>Publisher: {question.publisher.username}</p>
                    <p>Score: {question.score}</p>           
                        <button class="btn"><Link to={`/question/${question.id}`}>Ask Question</Link></button>
                   
                    
                  </div>
                </div>
              )
            )
          )}
        </div>
      }
        </div>
    )
        
    
}