<?php

namespace Database\Seeders;

use App\Models\Category;
use Illuminate\Database\Seeder;

class CategorySeeder extends Seeder
{
    public function run(): void
    {
        $categories = [
            ['name' => 'Alam',        'icon' => 'nature'],
            ['name' => 'Budaya',      'icon' => 'museum'],
            ['name' => 'Pantai',      'icon' => 'beach'],
            ['name' => 'Kuliner',     'icon' => 'restaurant'],
            ['name' => 'Religi',      'icon' => 'mosque'],
            ['name' => 'Petualangan', 'icon' => 'hiking'],
        ];

        foreach ($categories as $category) {
            Category::create($category);
        }
    }
}