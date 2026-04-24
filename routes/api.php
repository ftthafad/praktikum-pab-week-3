<?php

use App\Http\Controllers\Api\AuthController;
use App\Http\Controllers\Api\BookmarkController;
use App\Http\Controllers\Api\WisataController;
use Illuminate\Support\Facades\Route;

// ✅ Public Routes (tidak butuh token)
Route::prefix('auth')->group(function () {
    Route::post('/register', [AuthController::class, 'register']);
    Route::post('/login',    [AuthController::class, 'login']);
    Route::get('/google',          [AuthController::class, 'redirectToGoogle']);
    Route::get('/google/callback', [AuthController::class, 'handleGoogleCallback']);
});

// ✅ Wisata Public Routes
Route::prefix('wisata')->group(function () {
    Route::get('/',                      [WisataController::class, 'index']);
    Route::get('/{id}',                  [WisataController::class, 'show']);
    Route::get('/category/{categoryId}', [WisataController::class, 'byCategory']);
});

Route::get('/categories', [WisataController::class, 'categories']);

// ✅ Protected Routes (butuh token)
Route::middleware('auth:sanctum')->group(function () {
    Route::prefix('auth')->group(function () {
        Route::post('/logout', [AuthController::class, 'logout']);
        Route::get('/me',      [AuthController::class, 'me']);
    });

    // Bookmark Routes
    Route::prefix('bookmarks')->group(function () {
        Route::get('/',              [BookmarkController::class, 'index']);
        Route::post('/{wisataId}',   [BookmarkController::class, 'toggle']);
        Route::get('/{wisataId}',    [BookmarkController::class, 'check']);
    });
});