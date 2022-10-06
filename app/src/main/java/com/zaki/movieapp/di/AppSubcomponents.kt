package com.zaki.movieapp.di

import dagger.Module

@Module(
  subcomponents = [AuthComponent::class, MainComponent::class]
) class AppSubcomponents
