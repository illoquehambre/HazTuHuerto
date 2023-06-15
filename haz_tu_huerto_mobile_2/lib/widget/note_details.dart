import 'package:flutter/material.dart';
import 'package:haz_tu_huerto_mobile_2/config/locator.dart';
import 'package:haz_tu_huerto_mobile_2/models/note/note_response_dto.dart';
import 'package:haz_tu_huerto_mobile_2/pages/new_note_page.dart';
import 'package:haz_tu_huerto_mobile_2/rest/rest_client.dart';
import 'package:haz_tu_huerto_mobile_2/services/note_service.dart';
import 'package:haz_tu_huerto_mobile_2/widget/bottom_loader.dart';
import 'package:haz_tu_huerto_mobile_2/widget/note.dart';

class NoteDetails extends StatefulWidget {
  final NoteResponseDto note;
  final BuildContext context;

  const NoteDetails({super.key, required this.note, required this.context});

  @override
  State<NoteDetails> createState() => _NoteDetailsState();
}

class _NoteDetailsState extends State<NoteDetails> {
  final _scrollController = ScrollController();

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    _scrollController.addListener(_onScroll);
  }

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;
    final _noteService = getIt<NoteService>();
    return Material(
      child: Center(
        child: Column(
          children: <Widget>[
            Card(
              shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(10)),
              margin: EdgeInsets.all(15),
              elevation: 10,
              child: Column(
                children: <Widget>[
                  ListTile(
                    contentPadding: EdgeInsets.fromLTRB(15, 10, 25, 0),
                    title: Text(widget.note.title),
                    subtitle: Text(
                        widget.note.content),
                    leading: Text(widget.note.id.toString(),
                        style: textTheme.bodySmall),
                  ),
                  
                ],
              ),
            ),
            
          ],
        ),
      ),
    );
  }

  _onScroll() {
    setState(() {});
  }
}
