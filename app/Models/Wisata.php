<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Wisata extends Model
{
    use HasFactory;

    protected $table = 'wisata';

    protected $fillable = [
        'user_id',
        'category_id',
        'name',
        'description',
        'location',
        'latitude',
        'longitude',
        'price',
        'opening_hours',
        'rating',
        'review_count',
    ];

    public function category()
    {
        return $this->belongsTo(Category::class);
    }

    public function user()
    {
        return $this->belongsTo(User::class);
    }

    public function photos()
    {
        return $this->hasMany(Photo::class);
    }

    public function coverPhoto()
    {
        return $this->hasOne(Photo::class)->where('is_cover', true);
    }
    public function bookmarks()
    {
        return $this->hasMany(Bookmark::class);
    }
}