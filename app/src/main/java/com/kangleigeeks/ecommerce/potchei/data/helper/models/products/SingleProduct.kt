package com.kangleigeeks.ecommerce.potchei.data.helper.models.products

import java.util.*


class SingleProduct(val productId:String,val productName:String,val imagearray:ArrayList<ProductImage>,val rating:Int,val offer:Int){


    fun hasOffer(): Boolean {
        return offer>0
    }
}

class ProductImage (val sequenceId:Int,val url:String)
