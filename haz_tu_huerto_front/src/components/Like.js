import React, { useEffect, useState } from "react";
import { useLocation } from "wouter";




export default function Like(quest) {
    const apiUrl = `http://localhost:8080/question/`;
    const [location, setLocation] = useLocation();
    const [question, setQuestion]=useState(quest.quest)
    console.log(quest)
    console.log(quest.quest.likedByLoguedUser)
    const [like, setLike]=useState(quest.quest.likedByLoguedUser)

/*
    async function fetchQuest(id) {
        console.log(id)
        fetch(apiUrl+id, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json",
                "Authorization": `Bearer ${localStorage.getItem("token")}`,
            }
        })
        .then(res => {
            res.json();
        }).then((data) => {
            setQuest(data)
            console.log(data)
        })
        setIsLoading(false)
    
    };
*/
async function fetchLike(apiUrl,id) {
  
    return fetch(apiUrl +id+ '/like', {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("token")}`,
        }
    }).then(res => {

        if (res.ok) {
            return res.json();
        }
        throw new Error('Something went wrong');
    })
    .then((data) => {
        setQuestion(data)
        console.log(data)
        setLike(data.likedByLoguedUser)
    })
    .catch((error) => {
        console.log(error)
        setLocation('/Page404')
    });  
};

    const handleSubmit = async (e) => {
   
        const response = await fetchLike(apiUrl, quest.quest.id);
        
      };

   

   
    return (
         
        <div className="like">
           
            <img onClick={handleSubmit} src={like?'/img/heart.png':'/img/heart_1.png'}></img>
            
            <p>Likes: {question.score}</p>
        </div>
        
    );
}