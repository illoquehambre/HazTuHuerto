import React, { useEffect } from "react";

export default function Picture({keyword}){
    const apiUrl = `http://localhost:8080/download/`;
    async function fetchImage(name){
        const res = await fetch(apiUrl+'download/'+name, {
            method: "GET",
            headers: {
              "Content-Type": "image/jpeg",
              Accept: "application/json",
              Authorization: `Bearer ${localStorage.getItem("token")}`,
            }
        });
        const imageBlob = await res.blob();
        const imageObjectURL = URL.createObjectURL(imageBlob);
        setImg(imageObjectURL);
        return img;
      };
    useEffect(function(){
        fetchImage(keyword)
    })
    return(
        <div>
            
            <p>Token: {localStorage.getItem('username', 'user'['username'])}</p>
        </div>
        
    )
}