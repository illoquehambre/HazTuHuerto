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
      child: ListTile(
        leading: Text(widget.answer.id.toString(), style: textTheme.bodySmall),
        title: Text(widget.answer.content),
        subtitle: Text(widget.answer.createdAt),
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
