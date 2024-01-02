package com.qasimnawaz019.cartwave.ui.screens.checkout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.qasimnawaz019.cartwave.R
import com.qasimnawaz019.cartwave.ui.screens.address.AddAddressView
import com.qasimnawaz019.cartwave.ui.screens.graphs.MainScreenInfo
import com.qasimnawaz019.domain.model.Address

@Composable
fun CheckoutAddress(primaryAddress: MutableState<Address?>, onNavigate: (route: String) -> Unit) {
    if (primaryAddress.value != null) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth(),
        ) {
            val (rightSpacer, addressTitle, edit, map, title, address) = createRefs()

            Spacer(modifier = Modifier
                .width(0.dp)
                .wrapContentHeight()
                .constrainAs(rightSpacer) {
                    start.linkTo(parent.end)
                })

            Text(
                modifier = Modifier.constrainAs(addressTitle) {
                    start.linkTo(parent.start)
                },
                text = "Address",
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier
                    .constrainAs(edit) {
                        end.linkTo(parent.end)
                    }
                    .clickable { onNavigate.invoke(MainScreenInfo.Address.route) },
                text = "Edit",
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                color = MaterialTheme.colorScheme.primary
            )
            AsyncImage(
                modifier = Modifier
                    .width(100.dp)
                    .height(75.dp)
                    .clip(shape = MaterialTheme.shapes.medium)
                    .constrainAs(map) {
                        start.linkTo(parent.start)
                        top.linkTo(addressTitle.bottom, 12.dp)
                    },
                model = R.drawable.ic_map,
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )

            Text(
                modifier = Modifier.constrainAs(address) {
                    start.linkTo(map.absoluteRight, 10.dp)
                    end.linkTo(rightSpacer.absoluteLeft)
                    top.linkTo(map.bottom)
                    bottom.linkTo(map.top)
                    width = Dimension.fillToConstraints
                },
                text = "${primaryAddress.value?.address}",
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

        }
    } else {
        AddAddressView {
            onNavigate.invoke(MainScreenInfo.Address.route)
        }
    }
}