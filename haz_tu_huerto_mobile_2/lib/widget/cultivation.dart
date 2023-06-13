import 'package:flutter/material.dart';
import 'package:haz_tu_huerto_mobile_2/models/cultivation/cultivation_dto.dart';

class Cultivation extends StatefulWidget {
  final CultivationDto cultivation;
  final BuildContext context;
  final int num;

  const Cultivation(
      {super.key,
      required this.cultivation,
      required this.context,
      required this.num});

  @override
  State<Cultivation> createState() => _CultivationState();
}

class _CultivationState extends State<Cultivation> {
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
          /*
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
              return  CultivationDetailsPage(id: widget.cultivation.id);
            },
          ),
        );
      },*/
      child: ListTile(
        leading: Text(widget.cultivation.name, style: textTheme.bodySmall),
        title: Text(widget.cultivation.plantDate),
        isThreeLine: true,
        subtitle: Text(widget.cultivation.daysPlanted.toString()),
        textColor: Color.fromARGB(187, 0, 0, 0),
        dense: true,
      ),
    ));
  }

  _onScroll() {
    setState(() {});
  }
}
