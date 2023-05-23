import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/blocs/question/question_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/services/question_service.dart';
import 'package:haz_tu_huerto_mobile_2/widget/post_list_item.dart';
import 'package:loading_animation_widget/loading_animation_widget.dart';
import '../config/locator.dart';
import '../widget/bottom_loader.dart';

class QuestionPage extends StatelessWidget {
  const QuestionPage({super.key});

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) {
        final questionService = getIt<QuestionService>();
        return QuestionBloc(questionService)..add(QuestionInitialEvent());
      },
      child: EventsPageSF(),
    );
  }
}

class EventsPageSF extends StatefulWidget {
  const EventsPageSF({super.key});

  @override
  State<EventsPageSF> createState() => _EventsPageSFState();
}

class _EventsPageSFState extends State<EventsPageSF> {
  @override
  Widget build(BuildContext context) {
    return BlocBuilder<QuestionBloc, QuestionState>(
      builder: (context,  state) {
        if (state is QuestionSucces) {
          return QuestionList(
            state: state,
          );
        } else if (state is QuestionFailure) {
          return Center(
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

class QuestionList extends StatefulWidget {
  final QuestionSucces state;
  const QuestionList({super.key, required this.state});

  @override
  State<QuestionList> createState() => _QuestionListState();
}

class _QuestionListState extends State<QuestionList>
    with SingleTickerProviderStateMixin {
  final _scrollController = ScrollController();
  late bool showArrow = false;
  late bool showArrowF = false;
  late int currentTab = 0;

  @override
  void initState() {
    super.initState();
    _scrollController.addListener(_onScroll);
  }
  
@override
  Widget build(BuildContext context) {
    return ListView.builder(
              itemBuilder: (BuildContext context, int index) {
                return index >= widget.state.questions.length
                    ? const BottomLoader()
                    : Question(num:1, question: widget.state.questions[index],
                    context: context,);
              },
              itemCount: widget.state.questions.length,
              controller: _scrollController,
            );
  }
                 


  @override
  void dispose() {
    super.dispose();
    _scrollController
      ..removeListener(_onScroll)
      ..dispose();
/*
    _scrollControllerF
      ..removeListener(_onScrollF)
      ..dispose();
*/
  }


  void _onScroll() {
    if (_isTop) {
      setState(() {
        showArrow = true;
      });
    } else {
      setState(() {
        showArrow = false;
      });
    }
    if (_isBottom) {
      context.read<QuestionBloc>().add(QuestionScrollEvent());
    }
  }

  /*void _onScrollF() {
    if (_isTopF) {
      setState(() {
        showArrowF = true;
      });
    } else {
      setState(() {
        showArrowF = false;
      });
    }
   
  }
*/
  bool get _isBottom {
    if (!_scrollController.hasClients) return false;
    final maxScroll = _scrollController.position.maxScrollExtent;
    final currentScroll = _scrollController.offset;
    return currentScroll >= (maxScroll - 500);
  }

  bool get _isTop {
    if (!_scrollController.hasClients) return false;
    final currentScroll = _scrollController.offset;
    return currentScroll >= 750;
  }
/*
  bool get _isBottomF {
    if (!_scrollControllerF.hasClients) return false;
    final maxScroll = _scrollControllerF.position.maxScrollExtent;
    final currentScroll = _scrollControllerF.offset;
    return currentScroll >= (maxScroll - 500);
  }

  bool get _isTopF {
    if (!_scrollControllerF.hasClients) return false;
    final currentScroll = _scrollControllerF.offset;
    return currentScroll >= 750;
  }
*/
  Future loadlist() async {
    await Future.delayed(Duration(milliseconds: 1750));
    context.read<QuestionBloc>().add(QuestionRefreshEvent());
  }
/*
  void scrollToTopTab1() {
    _scrollController.animateTo(0,
        duration: Duration(milliseconds: 500), curve: Curves.easeInOut);
  }


  void scrollToTopTab2() {
    _scrollControllerF.animateTo(0,
        duration: Duration(milliseconds: 500), curve: Curves.easeInOut);
  }*/
}
