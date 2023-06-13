import 'package:flutter/material.dart';
import 'package:haz_tu_huerto_mobile_2/models/question/question_detail_dto.dart';
import 'package:haz_tu_huerto_mobile_2/widget/answer.dart';
import 'package:haz_tu_huerto_mobile_2/widget/bottom_loader.dart';

class QuestionDetails extends StatefulWidget {
  final QuestionDetailsDto question;
  final BuildContext context;

  const QuestionDetails({super.key, required this.question, required this.context});

  @override
  State<QuestionDetails> createState() => _QuestionDetailsState();
}

class _QuestionDetailsState extends State<QuestionDetails> {
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
      child: Center(
        child: Column(
          children: <Widget>[
            Text(
              widget.question.id.toString(),
              style: textTheme.bodySmall,
            ),
            Text(widget.question.title, style: textTheme.bodySmall),
             SizedBox(
              height: 350, // Establece una altura específica para el ListView
              child:ListView.builder(
              itemBuilder: (BuildContext context, int index) {
                return index >= widget.question.answers.length
                    ? const BottomLoader()
                    : Answer(num:1, answer: widget.question.answers[index],
                    context: context,);
              },
              itemCount: widget.question.answers.length),
             ),
            const SizedBox(
              height: 12,
            ),
           /* ElevatedButton(
              child: const Text('Update'),
              onPressed: () {
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
                      return UpdateGardenPage(id: widget.question.id);
                    },
                  ),
                );
              },
            ),*/
            /*
            ElevatedButton(
                onPressed: () async {
                  print("Check");
                  JwtAuthenticationService service =
                      getIt<JwtAuthenticationService>();
                  await service.getCurrentUser();
                },
                child: const Text('Check'))
                */
          ],
        ),
      ),
    );
  }

  _onScroll() {
    setState(() {});
  }
}
