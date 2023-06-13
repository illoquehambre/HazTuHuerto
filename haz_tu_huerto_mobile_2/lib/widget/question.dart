import 'package:flutter/material.dart';
import 'package:haz_tu_huerto_mobile_2/models/question/question_dto.dart';
import 'package:haz_tu_huerto_mobile_2/pages/question_details_page.dart';


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
              return  QuestionDetailsPage(id: widget.question.id);
            },
          ),
        );
      },
      child: ListTile(
        leading: Text(widget.question.id, style: textTheme.bodySmall),
        title: Text(widget.question.title),
        isThreeLine: true,
        subtitle: Text(widget.question.content),
        textColor: const Color.fromRGBO(126, 19, 126, 0.745),
        dense: true,
      ),
    ));
  }
   _onScroll() {
    setState(() {});
  }
}

