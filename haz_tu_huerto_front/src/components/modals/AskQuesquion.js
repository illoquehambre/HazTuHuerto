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
import { borderRadius, rgbToHex } from "@mui/system";
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
  whiteSpace: 'normal',
  flexDirection: 'column',
  flexDirection: 'column'
};
const btn ={
    position: "absolute",
    bottom: "5%",
    right: "5%",
    bgcolor: "purple",
    color: "white",

};


async function createQuest(credentials, id) {

  return fetch(`http://localhost:8080/answer/question/${id}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Accept": "application/json",
      "Authorization": `Bearer ${localStorage.getItem('token')}`,
    },
    body: JSON.stringify(credentials),
  }).then((data) =>{
    data.json()
    console.log(data)
  } );

  //localStorage.setItem(JSON.stringify(result))
}
export default function CreateQuestion(id) {
  
  const [location, setLocation] = useLocation();
  const [title, setTitle] = useState();
  const [content, setContent] = useState();
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const handleSubmit = async (e) => {
   
    const response = await createQuest({
      content,
    },id.id);
    handleClose()
    setLocation(location)
  };

  return (
    <div>
      <Button sx={btn} onClick={handleOpen}>Reply</Button>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <Typography id="modal-modal-title" variant="h6" component="h2">
           New Answer
          </Typography>

          <FormControl sx={{width:200}} onSubmit={handleSubmit}>
           
              <TextareaAutosize
                id="filled-textarea"
                minRows={3}                
                style={{ width: 200 }}
                
                placeholder="Content"               
                
                onChange={(e)=>setContent(e.target.value)}
              />
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
