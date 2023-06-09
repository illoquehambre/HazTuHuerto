import 'package:flutter/material.dart';
import 'package:haz_tu_huerto_mobile_2/models/note/note_simplify_dto.dart';

class Note extends StatefulWidget {
  final NoteSimplifyDto note;
  final BuildContext context;
  final int num;

  const Note(
      {super.key,
      required this.note,
      required this.context,
      required this.num});

  @override
  State<Note> createState() => _NoteState();
}

class _NoteState extends State<Note> {
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
    return Material(
        child: GestureDetector(
     /* onTap: () {
        Navigator.push(
          context,
          PageRouteBuilder(
            transitionDuration: const Duration(milliseconds: 500),
            transitionsBuilder:
                (context, animation, secondaryAnimation, child) {
              return FadeTransition(
                opacity: animation,
                child: child,
              );
            },
            pageBuilder: (context, animation, secondaryAnimation) {
              return  NoteDetailsPage(id: widget.note.id);
            },
          ),
        );
      },*/
      child: ListTile(
        leading: Text(widget.note.id as String, style: textTheme.bodySmall),
        title: Text(widget.note.title),
        isThreeLine: true,
        textColor: const Color.fromRGBO(126, 19, 126, 0.745),
        dense: true,
      ),
    ));
  }

  _onScroll() {
    setState(() {});
  }
}
