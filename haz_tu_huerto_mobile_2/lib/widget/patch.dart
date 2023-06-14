import 'package:flutter/material.dart';
import 'package:haz_tu_huerto_mobile_2/models/patch/patch_dto.dart';
import 'package:haz_tu_huerto_mobile_2/pages/patch_details_page.dart';
import 'package:haz_tu_huerto_mobile_2/rest/rest_client.dart';

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
                    return PatchDetailsPage(id: widget.patch.id);
                  },
                ),
              );
            },
            child: Card(
              shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(10)),
              margin: EdgeInsets.all(15),
              elevation: 10,
              child: Column(
                children: <Widget>[
                  ListTile(
                    contentPadding: EdgeInsets.fromLTRB(15, 10, 25, 0),
                    title: Text(widget.patch.name),
                    subtitle: Text(widget.patch.cultivation.name),
                    leading: Text(widget.patch.id.toString(),
                        style: textTheme.bodySmall),
                  ),
                  Container(
                    constraints: BoxConstraints(
                        maxHeight: 250), // Establece la altura máxima deseada
                    child: Image.network(
                      ApiConstants.baseUrl +
                          "/download/${widget.patch.cultivation.cultivationImg}",
                      fit: BoxFit.cover,
                    ),
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.end,
                    children: <Widget>[
                      Text(widget.patch.cultivation.daysLeft.toString()),
                    ],
                  )
                ],
              ),
            )));
  }

  _onScroll() {
    setState(() {});
  }
}
