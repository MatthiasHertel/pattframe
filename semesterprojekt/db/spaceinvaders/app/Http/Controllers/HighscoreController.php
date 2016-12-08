<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Highscore;

class HighscoreController extends Controller
{
    //


    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $highscores = Highscore::orderBy('id', 'desc')->paginate(10);
        return view('highscores.index')->withHighscores($highscores);
    }
}
