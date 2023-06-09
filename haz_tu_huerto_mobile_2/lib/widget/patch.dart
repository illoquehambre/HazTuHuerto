import 'package:flutter/material.dart';
import 'package:haz_tu_huerto_mobile_2/models/patch/patch_dto.dart';
import 'package:haz_tu_huerto_mobile_2/pages/patch_details_page.dart';

class Patch extends StatefulWidget {
  final PatchDto patch;
  final BuildContext context;
  final int num;

  const Patch(
      {super.key,
      required this.patch,
      required this.context,
      required this.num});

  @override
  State<Patch> createState() => _PatchState();
}

class _PatchState extends State<Patch> {
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
              return  PatchDetailsPage(id: widget.patch.id as String);
            },
          ),
        );
      },
      child: ListTile(
        leading: Text(widget.patch.id as String, style: textTheme.bodySmall),
        title: Text(widget.patch.name),
        isThreeLine: true,
        subtitle: Text(widget.patch.cultivation.name),
        textColor: const Color.fromRGBO(126, 19, 126, 0.745),
        dense: true,
      ),
    ));
  }

  _onScroll() {
    setState(() {});
  }
}
