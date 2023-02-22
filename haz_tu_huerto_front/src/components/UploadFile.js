import React, { ChangeEvent, useState } from 'react';
import { useEffect } from "react";

export default function FileUploadSingle() {
    const apiUrl = `http://localhost:8080/user/`

        const [selectedFile, setSelectedFile] = useState();
        const [isSelected, setIsSelected] = useState(false);
        console.log()
        const changeHandler = (event) => {//cambiar este método para pillarlo como en login
            setSelectedFile(event.target.files[0]);
            console.log(event.target.value)//Este array está vacio pero no debería
            console.log(event.target.files)
            setIsSelected(true);
        };

        const handleSubmit = async (e) => {
            e.preventDefault();
            const formData = new FormData();
            const name = {
                "fullName": "Pacooooo"
            }
            console.log(JSON.parse(JSON.stringify(name)))
            formData.append('file', selectedFile);
            console.log(selectedFile)
            formData.append('editUser', JSON.parse(JSON.stringify(name)) )

            fetch(
                apiUrl,
                {
                    method: 'PUT',
                    body: formData,
                    
                    headers: {
                    "Content-Type":"multipart/form-data",
                    "Accept": "*/*",
                    "Authorization": `Bearer ${localStorage.getItem('token')}`,
                    "Access-Control-Allow-Origin":"*"
                    }
                }
            )
                .then((response) => response.json())
                .then((result) => {
                    console.log('Success:', result);
                })
                .catch((error) => {
                    console.error('Error:', error);
                });
        };
    

    return (
        <div>
            <input type="file" name="file" onChange={changeHandler} />
            {isSelected ? (
                <div>
                    <p>Filename: {selectedFile.name}</p>
                    <p>Filetype: {selectedFile.type}</p>
                    <p>Size in bytes: {selectedFile.size}</p>
                    <p>
                        lastModifiedDate:{' '}
                        {selectedFile.lastModifiedDate.toLocaleDateString()}
                    </p>
                </div>
            ) : (
                <p>Select a file to show details</p>
            )}
            <div>
                <button onClick={handleSubmit}>Submit</button>
            </div>
        </div>
    )
}

