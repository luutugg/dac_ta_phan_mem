package com.example.dactaphanmem.common

import com.example.dactaphanmem.common.eventbus.IEvent
import com.example.dactaphanmem.data.entity.Book

class ResolveViolate(val isResolve: Boolean) : IEvent

class OnUpdateBook(val book: Book?) : IEvent

class OnInsertBook(val book: Book?): IEvent

class OnDeleteBook(val bookId: String?): IEvent
