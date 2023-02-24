import React from "react";
import { useEffect, useState } from "react";
import { useLocation } from 'wouter';
import NavBar from './NavBar.js'
import { Link } from 'wouter';
import "../styles/List.css";



export default function QuestionList() {
  const [location, setLocation] = useLocation();
  const [isLoading, setIsLoading] = useState(true);
  const [page, setPage] = useState()
  const [quests, setQuests] = useState([])
  const [numPage, setNumPage] = useState(0)
  const name = location.split('/').reverse()[0]
  console.log(name)
  const apiUrl = `http://localhost:8080/question`

  const sum = async (e) => {
    setNumPage(numPage + 1)
  };
  const res = async (e) => {
    setNumPage(numPage - 1)
  };

  useEffect(function () {
    fetch(`${apiUrl}?page=${numPage}&size=3`, {
      method: 'GET',
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json",
        "Authorization": `Bearer ${localStorage.getItem('token')}`
      }
    })
      .then(res => {

        if (res.ok) {
          return res.json();
        }
        throw new Error('Something went wrong');
      })
      .then((data) => {
        setPage(data)
        setQuests(data.content)
        console.log(data.content)
        if (data.last){
          if(data.first){
            document.getElementById('decrease').style.display = 'none';
          }else{
            document.getElementById('decrease').style.display = 'inline';
          }
          document.getElementById('increase').style.display = 'none';
        }
        else if (data.first){
          if(!data.last){
            document.getElementById('increase').style.display = 'inline';
          }
          document.getElementById('decrease').style.display = 'none';
        }
        else {
          document.getElementById('decrease').style.display = 'inline';
          document.getElementById('increase').style.display = 'inline';

        }
      })
      .catch((error) => {
        console.log(error)
        setLocation('/Page404')
      });
    setIsLoading(false)


  }, [numPage])


  if (isLoading) {
    return (
      <div className="App">
        <h1>Cargando...</h1>
      </div>
    );
  }
  return (
    <div>
      <NavBar></NavBar>
      <div>
        <a id="decrease" onClick={res}>-</a>
        <span>{numPage}</span>
        <a id="increase" onClick={sum}>+</a>
      </div>
      {
        <div className="courses-container">
          {quests.map(
            (question) => (
              console.log(question),
              (
                <div className="course" key={question.id}>
                  <div className="course-preview">
                    <h6>{question.publisher.username}</h6>
                    <h3>{question.title}</h3>
                    <p>Score: {question.score}</p>

                  </div>
                  <div className="course-info">
                    <div className="progress-container">
                      <span class="progress-text">
                        {question.createdAt}
                      </span>
                    </div>

                    <h6>{question.content}</h6>
                    <button className="btn"><Link to={`/question/${question.id}`}>Details</Link></button>

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