import * as React from "react";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import Typography from "@mui/material/Typography";
import Modal from "@mui/material/Modal";
import { useLocation } from "wouter";
import Swal from "sweetalert2";
import { useEffect, useState } from "react";
import TextField from "@mui/material/TextField";
import LoadingButton from '@mui/lab/LoadingButton';
import SaveIcon from '@mui/icons-material/Save';
import Stack from '@mui/material/Stack';
import TextareaAutosize from '@mui/base/TextareaAutosize';
const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
};
const center = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
};

async function createQuest(credentials) {
  /*
  const [selectedFile, setSelectedFile] = useState();
  const [isSelected, setIsSelected] = useState(false);
  const formData = new FormData();
            const name = {
                "title": "Pacooooo",
                "content": "aaaaaaaaaaaaaaaaaaaaaaaaa"
            }
            console.log(JSON.parse(JSON.stringify(name)))
            formData.append('file', selectedFile);
            console.log(selectedFile)

            formData.append('newQuest', JSON.parse(JSON.stringify(name)) )
            */
  return fetch("http://localhost:8080/question/", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Accept": "application/json",
      "Authorization": `Bearer ${localStorage.getItem('token')}`,
    },
    body: JSON.stringify(credentials),
  }).then((data) => data.json());

  //localStorage.setItem(JSON.stringify(result))
}
export default function CreateQuestion() {
  
  const [, setLocation] = useLocation();
  const [title, setTitle] = useState();
  const [content, setContent] = useState();
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const handleSubmit = async (e) => {
   
    const response = await createQuest({
      title,
      content,
    });
    if ("token" in response) {
      console.log(response);
      Swal.fire({
        icon: "success",
        title: "oleeee",
        text: "Welcome",
        showConfirmButton: false,
        timer: 2000,
      }).then(() => {
        const name = response["username"];
        //localStorage.setItem('user', JSON.stringify(response['user']));
        handleClose()
      });
    } else {
      console.log(response);
      console.log(response.status);
      Swal.fire({
        icon: "error",
        title: "Oops...",
        text: response.message,
      });
    }
  };

  return (
    <div>
      <Button onClick={handleOpen}>Open modal</Button>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <Typography id="modal-modal-title" variant="h6" component="h2">
           New Question
          </Typography>

          <FormControl center  fullWidth onSubmit={handleSubmit}>
            <Box className="user-box">
              <TextField
                id="filled-textarea"
                label="Title"
                placeholder="Placeholder"
                variant="standard"
                onChange={(e)=>setTitle(e.target.value)}
              />
            </Box>
            <Box  className="user-box">
              <TextareaAutosize
                id="filled-textarea"
                minRows={3}                
                style={{ width: 200 }}
                
                placeholder="Content"               
                
                onChange={(e)=>setContent(e.target.value)}
              />
            </Box>
            <Stack direction="row" spacing={2}>
              <LoadingButton
                loading={false}
                
                startIcon={<SaveIcon />}
                variant="outlined"
                onClick={() => {
                    handleSubmit();
                  }}
              >
                Save
              </LoadingButton>
            </Stack>
          </FormControl>
        </Box>
      </Modal>
    </div>
  );
}
