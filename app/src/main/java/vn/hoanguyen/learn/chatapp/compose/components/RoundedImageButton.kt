package vn.hoanguyen.learn.chatapp.compose.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vn.hoanguyen.learn.chatapp.compose.R

@Composable
fun RoundedImageButton(
    @DrawableRes imageRes: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .shadow(elevation = 1.dp, shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier,
            contentDescription = null,
            painter = painterResource(id = imageRes),
        )
    }
}

@Preview
@Composable
private fun RoundedImageButtonPreview() {
    RoundedImageButton(
        imageRes = R.drawable.ic_google,
        onClick = {}
    )
}