import 'package:flutter/material.dart';
import 'package:haz_tu_huerto_mobile_2/models/answer/answer_dto.dart';

class Answer extends StatefulWidget {
  final AnswerDto answer;
  final BuildContext context;
  final int num;

  const Answer(
      {super.key,
      required this.answer,
      required this.context,
      required this.num});

  @override
  State<Answer> createState() => _AnswerState();
}

class _AnswerState extends State<Answer> {
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
              return  AnswerDetailsPage(id: widget.answer.id);
            },
          ),
        );
      },*/
      child:  Card(
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)),
        margin: EdgeInsets.all(15),
        elevation: 10,
        child: Column(
          children: <Widget>[
            ListTile(
              contentPadding: EdgeInsets.fromLTRB(15, 10, 25, 0),
              title: Text(widget.answer.publisher.username.toString()),
              subtitle: Text(widget.answer.content),
              leading:
                  Text(widget.answer.id.toString(), style: textTheme.bodySmall),
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.end,
              children: <Widget>[
             // Espacio entre el primer Text y el segundo
                 Text(widget.answer.createdAt, style: textTheme.bodySmall),                
                
              ],
            ),
          ],
        ),
      ),
    ));
  }

  _onScroll() {
    setState(() {});
  }
}
