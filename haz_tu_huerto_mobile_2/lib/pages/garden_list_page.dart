import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/blocs/garden/garden_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/config/locator.dart';
import 'package:haz_tu_huerto_mobile_2/pages/new_garden_page.dart';
import 'package:haz_tu_huerto_mobile_2/services/garden_service.dart';
import 'package:haz_tu_huerto_mobile_2/widget/bottom_loader.dart';
import 'package:haz_tu_huerto_mobile_2/widget/garden.dart';
import 'package:loading_animation_widget/loading_animation_widget.dart';


class GardenPage extends StatelessWidget {
  const GardenPage({super.key});

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) {
        final gardenService = getIt<GardenService>();
        return GardenBloc(gardenService)..add(GardenInitialEvent());
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
    return BlocBuilder<GardenBloc, GardenState>(
      builder: (context,  state) {
        if (state is GardenSucces) {
          return GardenList(
            state: state,
          );
        } else if (state is GardenFailure) {
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

class GardenList extends StatefulWidget {
  final GardenSucces state;
  const GardenList({super.key, required this.state});

  @override
  State<GardenList> createState() => _GardenListState();
}

class _GardenListState extends State<GardenList>
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
    return Material(
      child: Center(
          child: Column(
            children: <Widget>[
      
        
              SizedBox(
              height: 350, // Establece una altura específica para el ListView
              child: ListView.builder(
              itemBuilder: (BuildContext context, int index) {
                return index >= widget.state.garden.length
                    ? const BottomLoader()
                    : Garden(num:1, garden: widget.state.garden[index],
                    context: context,);
              },
              itemCount: widget.state.garden.length,
              controller: _scrollController,
            ),
                ),
              const SizedBox(
                height: 12,
              ),
              ElevatedButton(
                child: const Text('New Garden'),
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
                        return NewGardenPage();
                      },
                    ),
                  );
                },
              ),
              ],
          ),
      ),
    );
    
    
    
    
    
    /*ListView.builder(
              itemBuilder: (BuildContext context, int index) {
                return index >= widget.state.garden.length
                    ? const BottomLoader()
                    : Garden(num:1, garden: widget.state.garden[index],
                    context: context,);
              },
              itemCount: widget.state.garden.length,
              controller: _scrollController,
            );
            */
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
      context.read<GardenBloc>().add(GardenScrollEvent());
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
    await Future.delayed(const Duration(milliseconds: 1750));
    context.read<GardenBloc>().add(GardenRefreshEvent());
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
