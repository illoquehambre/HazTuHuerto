import 'package:blurrycontainer/blurrycontainer.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_form_bloc/flutter_form_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/blocs/new_note_form/new_note_form_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/services/note_service.dart';


import '../config/locator.dart';

class NewNotePage extends StatefulWidget {
   final int id;
  const NewNotePage({super.key, required this.id});

  @override
  State<NewNotePage> createState() => _NewNotePageState();
}

class _NewNotePageState extends State<NewNotePage> {
  @override
  Widget build(BuildContext context) {
    final _noteService = getIt<NoteService>();
    return Scaffold(
      appBar: AppBar(
        title: const Text('Patch Details'),
      ),
      body:BlocProvider(
      create: (context) => NewNoteFormBloc(_noteService, widget.id),
      child: Builder(
        builder: (context) {
          final formBloc = context.read<NewNoteFormBloc>();
          return FormBlocListener<NewNoteFormBloc, String, String>(
            onSuccess: (context, state) {
              showOk(context);
              formBloc.clear();
              setState(() {});
            },
            child: NewNotePageSF(formBloc: formBloc),
          );
        },
      ),
      ),
    );
  }
}

class NewNotePageSF extends StatefulWidget {
  final NewNoteFormBloc formBloc;
  const NewNotePageSF({super.key, required this.formBloc});

  @override
  State<NewNotePageSF> createState() => _NewNotePageSFState();
}

class _NewNotePageSFState extends State<NewNotePageSF> {
  


  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Container(
        width: double.infinity,
        alignment: Alignment.center,
        padding: EdgeInsets.only(left: 20, right: 20),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          children: [
            SizedBox(
              height: 90,
            ),
            BlurryContainer(
              borderRadius: BorderRadius.all(
                Radius.circular(10),
              ),
              color: Colors.white.withOpacity(0.35),
              blur: 8,
              elevation: 6,
              padding: EdgeInsets.all(20),
              child: Column(
                children: [
                  Container(
                    decoration: BoxDecoration(
                      color: Colors.white,
                      borderRadius: BorderRadius.all(
                        Radius.circular(10),
                      ),
                    ),
                    padding: EdgeInsets.fromLTRB(20, 10, 20, 10),
                    child: Column(
                      children: [
                        TextFieldBlocBuilder(
                          textFieldBloc: widget.formBloc.title,
                          decoration: InputDecoration(
                            border: OutlineInputBorder(
                              borderSide: BorderSide(
                                color: Color.fromRGBO(217, 217, 217, 1),
                              ),
                              borderRadius: BorderRadius.all(
                                Radius.circular(10),
                              ),
                            ),
                            labelText: 'Note Name',
                            labelStyle: TextStyle(fontSize: 20),
                            fillColor: Colors.white,
                            contentPadding: EdgeInsets.fromLTRB(10, 20, 10, 20),
                            filled: true,
                            isDense: true,
                            focusedBorder: OutlineInputBorder(
                              borderRadius: BorderRadius.all(
                                Radius.circular(10),
                              ),
                              borderSide: BorderSide(
                                  color: Color.fromRGBO(173, 29, 254, 1),
                                  width: 1),
                            ),
                          ),
                        ),
                        SizedBox(
                          height: 10,
                        ),
                        
                        TextFieldBlocBuilder(
                          maxLines: null,
                          minLines: 8,
                          maxLength: 250,
                          buildCounter: (context,
                              {required currentLength,
                              required isFocused,
                              maxLength}) {
                            return Row(
                              mainAxisAlignment: MainAxisAlignment.center,
                              mainAxisSize: MainAxisSize.max,
                              children: [
                                Container(
                                  width: 150,
                                  height: 3,
                                  decoration: BoxDecoration(
                                    color: Colors.grey,
                                    borderRadius: BorderRadius.all(
                                      Radius.circular(5),
                                    ),
                                  ),
                                  child: Align(
                                    alignment: Alignment.centerLeft,
                                    child: Container(
                                      width:
                                          (currentLength.ceilToDouble() * 200) /
                                              250,
                                      height: 3,
                                      color: Color.fromRGBO(90, 186, 107, 1),
                                    ),
                                  ),
                                ),
                                SizedBox(
                                  width: 20,
                                ),
                                Text("$currentLength/$maxLength")
                              ],
                            );
                          },
                          textFieldBloc: widget.formBloc.content,
                          decoration: InputDecoration(
                            border: OutlineInputBorder(
                              borderSide: BorderSide(
                                color: Color.fromRGBO(217, 217, 217, 1),
                              ),
                              borderRadius: BorderRadius.all(
                                Radius.circular(10),
                              ),
                            ),
                            labelText: 'Content',
                            alignLabelWithHint: true,
                            labelStyle: TextStyle(fontSize: 20),
                            fillColor: Colors.white,
                            contentPadding: EdgeInsets.fromLTRB(10, 20, 10, 20),
                            filled: true,
                            isDense: true,
                            focusedBorder: OutlineInputBorder(
                              borderRadius: BorderRadius.all(
                                Radius.circular(10),
                              ),
                              borderSide: BorderSide(
                                  color: Color.fromRGBO(173, 29, 254, 1),
                                  width: 1),
                            ),
                          ),
                        ),
                      ],
                    ),
                  ),
                  SizedBox(
                    height: 20,
                  ),
                  
                  Container(
                    width: double.infinity,
                    child: ElevatedButton(
                      style: ButtonStyle(
                        backgroundColor: MaterialStateProperty.all<Color>(
                          Color.fromRGBO(27, 211, 27, 1),
                        ),
                        padding: MaterialStateProperty.all<EdgeInsets>(
                            EdgeInsets.all(10)),
                        shape:
                            MaterialStateProperty.all<RoundedRectangleBorder>(
                          RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(10.0),
                          ),
                        ),
                      ),
                      child: Text('Publicar',
                          style: TextStyle(color: Colors.white, fontSize: 40)),
                      onPressed: () {
                        widget.formBloc.submit();
                      },
                    ),
                  )
                ],
              ),
            )
          ],
        ),
      ),
    );
  }
}

ScaffoldFeatureController<SnackBar, SnackBarClosedReason> showOk(
    BuildContext context) {
  return ScaffoldMessenger.of(context).showSnackBar(
    SnackBar(
      backgroundColor: Colors.transparent,
      content: Container(
        padding: const EdgeInsets.all(8),
        height: 80,
        decoration: BoxDecoration(
            color: Colors.green,
            borderRadius: BorderRadius.all(Radius.circular(10))),
        child: Row(
          children: [
            Icon(
              Icons.check_circle_outline,
              color: Colors.white,
              size: 40,
            ),
            SizedBox(
              width: 20,
            ),
            Expanded(
                child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  "¡Todo bien!",
                  style: TextStyle(
                      fontSize: 18,
                      color: Colors.white,
                      fontWeight: FontWeight.bold),
                ),
                Spacer(),
                Text(
                  "Tu pregunta se ha publicado con exito",
                  style: TextStyle(color: Colors.white, fontSize: 15),
                  maxLines: 2,
                  overflow: TextOverflow.ellipsis,
                )
              ],
            ))
          ],
        ),
      ),
    ),
  );
}
