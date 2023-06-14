import 'package:flutter/material.dart';
import 'package:haz_tu_huerto_mobile_2/models/question/question_dto.dart';
import 'package:haz_tu_huerto_mobile_2/pages/question_details_page.dart';
import 'package:haz_tu_huerto_mobile_2/rest/rest_client.dart';

class Question extends StatefulWidget {
  final QuestionDto question;
  final BuildContext context;
  final int num;

  const Question(
      {super.key,
      required this.question,
      required this.context,
      required this.num});

  @override
  State<Question> createState() => _QuestionState();
}

class _QuestionState extends State<Question> {
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
      onTap: () {
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
              return QuestionDetailsPage(id: widget.question.id);
            },
          ),
        );
      },
      child: Card(
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)),
        margin: EdgeInsets.all(15),
        elevation: 10,
        child: Column(
          children: <Widget>[
            ListTile(
              contentPadding: EdgeInsets.fromLTRB(15, 10, 25, 0),
              title: Text(widget.question.title),
              subtitle: Text(widget.question.content),
              leading:
                  Text(widget.question.id.toString(), style: textTheme.bodySmall),
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.end,
              children: <Widget>[
             // Espacio entre el primer Text y el segundo
                 Text('Answers: '+widget.question.answers.toString(), style: textTheme.bodySmall),
                
                
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
