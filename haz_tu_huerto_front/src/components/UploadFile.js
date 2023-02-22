import React, { ChangeEvent, useState } from 'react';
import { useEffect } from "react";

export default function FileUploadSingle(id) {
    const apiUrl = `http://localhost:8080/user/${id}`
  
        const [selectedFile, setSelectedFile] = useState();
        const [isSelected, setIsSelected] = useState(false);

        const changeHandler = (event) => {
            setSelectedFile(event.target.files[0]);
            setIsSelected(true);
        };

        const handleSubmit = async (e) => {
            e.preventDefault();
            const formData = new FormData();
            const name = {
                "fullName": "Pacooooo"
            }
            console.log(JSON.parse(JSON.stringify(name)))
            formData.append('File', selectedFile);
            formData.append('editUser', JSON.parse(JSON.stringify(name)) )

            fetch(
                apiUrl,
                {
                    method: 'POST',
                    body: formData,
                    Accept: "application/json",
                    Authorization: `Bearer ${localStorage.getItem('token')}`
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

