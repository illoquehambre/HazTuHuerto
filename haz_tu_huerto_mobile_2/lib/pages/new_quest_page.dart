import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_form_bloc/flutter_form_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/blocs/new_question_form/new_question_form_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/config/locator.dart';
import 'package:haz_tu_huerto_mobile_2/services/question_service.dart';
import 'package:image_picker/image_picker.dart';

/*

class NewQuestionPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final _questService = getIt<QuestionService>();
    return BlocProvider(
      create: (context) => NewQuestFormBloc(_questService),
      child: Builder(
        builder: (context) {
          final formBloc = context.read<NewQuestFormBloc>();

          return Scaffold(
            appBar: AppBar(
              title: Text('Formulario'),
            ),
            body: FormBlocListener<NewQuestFormBloc, String, String>(
              onSubmitting: (context, state) {
                // Mostrar indicador de carga
                showDialog(
                  context: context,
                  builder: (BuildContext context) {
                    return AlertDialog(
                      content: Row(
                        children: [
                          CircularProgressIndicator(),
                          SizedBox(width: 20),
                          Text("Enviando formulario..."),
                        ],
                      ),
                    );
                  },
                );
              },
              onSuccess: (context, state) {
                // Cerrar el diálogo y mostrar mensaje de éxito
                Navigator.of(context).pop();
                showDialog(
                  context: context,
                  builder: (BuildContext context) {
                    return AlertDialog(
                      content: Text("Formulario enviado con éxito"),
                      actions: [
                        TextButton(
                          onPressed: () {
                            Navigator.of(context).pop();
                          },
                          child: Text("OK"),
                        ),
                      ],
                    );
                  },
                );
              },
              onFailure: (context, state) {
                // Cerrar el diálogo y mostrar mensaje de error
                Navigator.of(context).pop();
                showDialog(
                  context: context,
                  builder: (BuildContext context) {
                    return AlertDialog(
                      content: Text("Error al enviar el formulario"),
                      actions: [
                        TextButton(
                          onPressed: () {
                            Navigator.of(context).pop();
                          },
                          child: Text("OK"),
                        ),
                      ],
                    );
                  },
                );
              },
              child: ListView(
                padding: EdgeInsets.all(20),
                children: [
                  ImageInputField(
                    imageField: formBloc.files,
                    title: 'Imagen',
                  ),
                  TextFieldBlocBuilder(
                    textFieldBloc: formBloc.title,
                    decoration: InputDecoration(
                      labelText: 'Atributo 1',
                    ),
                  ),
                  TextFieldBlocBuilder(
                    textFieldBloc: formBloc.content,
                    decoration: InputDecoration(
                      labelText: 'Atributo 2',
                    ),
                  ),
                  SizedBox(height: 20),
                  ElevatedButton(
                    onPressed: formBloc.submit,
                    child: Text('Enviar'),
                  ),
                ],
              ),
            ),
          );
        },
      ),
    );
  }
}



class ImageInputField extends StatelessWidget {
  final List<XFile> imageField;
  final String title;

  const ImageInputField({
    required this.imageField,
    required this.title,
  });

  @override
  Widget build(BuildContext context) {
  var imageField = context.watch<XFile>();

  return Column(
    crossAxisAlignment: CrossAxisAlignment.start,
    children: [
      Text(title),
      SizedBox(height: 10),
      GestureDetector(
        onTap: () async {
          final pickedFile = await ImagePicker().pickImage(source: ImageSource.gallery);
          if (pickedFile != null) {
            imageField=pickedFile;
          }
        },
        child: Container(
          height: 150,
          width: double.infinity,
          color: Colors.grey[200],
          decoration: BoxDecoration(
                                  borderRadius: BorderRadius.all(
                                    Radius.circular(10),
                                  ),
                                  image: DecorationImage(
                                      fit: BoxFit.cover,
                                      image: FileImage(
                                        File(imageField.path),
                                      )),
                                ),
      ),
      ),
    ],
  );
  }
}

*/