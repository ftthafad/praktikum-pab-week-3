<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Photo extends Model
{
    use HasFactory;

    protected $fillable = [
        'wisata_id',
        'photo_url',
        'is_cover',
    ];

    public function wisata()
    {
        return $this->belongsTo(Wisata::class);
    }
}