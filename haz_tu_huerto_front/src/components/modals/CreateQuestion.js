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

async function createQuest(credentials) {
  return fetch("http://localhost:8080/auth/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Accept: "application/json",
    },
    body: JSON.stringify(credentials),
  }).then((data) => data.json());

  //localStorage.setItem(JSON.stringify(result))
}
export default function CreateQuestion() {
  const [, setLocation] = useLocation();
  const [title, setTitle] = useState();
  const [content, setContent] = useState();
  const [username, setUsername] = useState();
  const [password, setPassword] = useState();
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const response = await createQuest({
      username,
      password,
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
        localStorage.setItem("token", response["token"]);
        console.log(localStorage.getItem("token"));
        const name = response["username"];
        //localStorage.setItem('user', JSON.stringify(response['user']));
        setLocation(`/user/${name}`);
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
            Text in a modal
          </Typography>

          <FormControl onSubmit={handleSubmit}>
            <Box className="user-box">
              <TextField
                id="filled-textarea"
                label="Multiline Placeholder"
                placeholder="Placeholder"
                multiline
                variant="standard"
                onChange={(e)=>setUsername(e.target.value)}
              />
            </Box>
            <Box className="user-box">
              <TextField
                id="filled-textarea"
                label="Multiline Placeholder"
                placeholder="Placeholder"
                multiline
                variant="standard"
                onChange={(e)=>setUsername(e.target.value)}
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
