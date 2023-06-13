
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/blocs/question_details/question_details_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/config/locator.dart';
import 'package:haz_tu_huerto_mobile_2/services/question_service.dart';
import 'package:haz_tu_huerto_mobile_2/widget/question_details.dart';
import 'package:loading_animation_widget/loading_animation_widget.dart';

class QuestionDetailsPage extends StatelessWidget {
  final String id;
  const QuestionDetailsPage({super.key,required this.id});

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) {
        final questionService = getIt<QuestionService>();
        return QuestionDetailsBloc(questionService)..add(QuestionDetailsInitialEvent(id: id));
      },
      child: const EventsPageSFG(),
    );
  }
}

class EventsPageSFG extends StatefulWidget {
  const EventsPageSFG({super.key});

  @override
  State<EventsPageSFG> createState() => _EventsPageSFGState();
}

class _EventsPageSFGState extends State<EventsPageSFG> {
  @override
  Widget build(BuildContext context) {
    return BlocBuilder<QuestionDetailsBloc, QuestionDetailsState>(
      builder: (context,  state) {
        if (state is QuestionDetailsSucces) {
          return QuestionDetails(
            context: context,
            question: state.question,
          );
        } else if (state is QuestionDetailsFailure) {
          return const Center(
            child: Text("Ha ocurrido un error a la hora de cargar los posts"),
          );
        } else {
          return Center(
            child: LoadingAnimationWidget.staggeredDotsWave(
                color: Colors.white, size: 40),
          );
        }
      },
    );
  }
}